let currentPdfDoc = null;
let currentPage = 1;

document.addEventListener('DOMContentLoaded', () => {
    const pdfViewer = document.getElementById('pdfViewer');
    if (!pdfViewer) return;

    const pdfJsScript = document.createElement('script');
    pdfJsScript.src = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.min.js';
    pdfJsScript.async = true;
    document.head.appendChild(pdfJsScript);

    pdfJsScript.onload = function() {
        pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js';

        const pdfUrl = pdfViewer.dataset.pdfUrl;
        console.log("PDF URL from dataset:", pdfUrl);

        initPdfViewer(pdfUrl);
        setupChat();
    };

    window.addEventListener('resize', () => {
        if (currentPdfDoc) {
            renderPage(currentPdfDoc, currentPage);
        }
    });
});

async function initPdfViewer(pdfUrl) {
    try {
        console.log("Loading PDF from:", pdfUrl);

        const canvas = document.getElementById("canvas");
        const ctx = canvas.getContext("2d");
        ctx.font = "24px Arial";
        ctx.fillStyle = "#333";
        ctx.textAlign = "center";
        ctx.fillText("Loading document...", canvas.width/2, canvas.height/2);

        const loadingTask = pdfjsLib.getDocument(pdfUrl);
        const doc = await loadingTask.promise;

        currentPdfDoc = doc;

        console.log("PDF loaded successfully with", doc.numPages, "pages");

        // Get document information
        const minPage = 1;
        const maxPage = doc.numPages;
        currentPage = 1;

        await renderPage(doc, currentPage);

        document.getElementById("pageNumber").innerHTML = `Page ${currentPage} of ${maxPage}`;

        document.getElementById("previous").addEventListener("click", async () => {
            if (currentPage > minPage) {
                currentPage--;
                await renderPage(doc, currentPage);
                document.getElementById("pageNumber").innerHTML = `Page ${currentPage} of ${maxPage}`;
            }
        });

        document.getElementById("next").addEventListener("click", async () => {
            if (currentPage < maxPage) {
                currentPage++;
                await renderPage(doc, currentPage);
                document.getElementById("pageNumber").innerHTML = `Page ${currentPage} of ${maxPage}`;
            }
        });

        document.addEventListener('keydown', async (e) => {
            if (e.key === 'ArrowRight' && currentPage < maxPage) {
                currentPage++;
                await renderPage(doc, currentPage);
                document.getElementById("pageNumber").innerHTML = `Page ${currentPage} of ${maxPage}`;
            } else if (e.key === 'ArrowLeft' && currentPage > minPage) {
                currentPage--;
                await renderPage(doc, currentPage);
                document.getElementById("pageNumber").innerHTML = `Page ${currentPage} of ${maxPage}`;
            }
        });
    } catch (error) {
        console.error("Error initializing PDF viewer:", error);
        document.getElementById("pageNumber").innerHTML = "Error loading PDF. Check console for details.";
    }
}

async function renderPage(pdfDoc, pageNumber) {
    try {
        console.log("Attempting to render page", pageNumber);
        const page = await pdfDoc.getPage(pageNumber);
        console.log("Page object obtained");

        const canvas = document.getElementById("canvas");
        if (!canvas) {
            console.error("Canvas element not found in renderPage");
            return;
        }
        console.log("Canvas element found");

        const viewportContainer = document.querySelector('.d-flex.justify-content-center');
        if (!viewportContainer) {
            console.error("Viewport container not found");
            return;
        }

        const containerWidth = viewportContainer.clientWidth;
        const containerHeight = viewportContainer.clientHeight;
        console.log("Container dimensions:", containerWidth, containerHeight);

        // Set scale to fit within container
        const viewport = page.getViewport({ scale: 1.0 });
        const scaleX = containerWidth / viewport.width;
        const scaleY = containerHeight / viewport.height;
        const scale = Math.min(scaleX, scaleY) * 0.9; // Use 90% of available space

        const scaledViewport = page.getViewport({ scale });
        const context = canvas.getContext("2d");

        canvas.width = scaledViewport.width;
        canvas.height = scaledViewport.height;

        const renderContext = {
            canvasContext: context,
            viewport: scaledViewport
        };

        await page.render(renderContext).promise;
        console.log("Page render process completed successfully");

    } catch (error) {
        console.error("Error during renderPage execution:", error);
    }
}

async function getCurrentPageText() {
    if (!currentPdfDoc) {
        return "";
    }
    try {
        const page = await currentPdfDoc.getPage(currentPage);
        const textContent = await page.getTextContent();
        return textContent.items.map(item => item.str).join(' ');
    } catch (error) {
        console.error("Error getting page text:", error);
        return "";
    }
}

function setupChat() {
    console.log("Attempting to set up chat...");
    const chatToggle = document.getElementById('chatToggle');
    const chatSidebar = document.getElementById('chatSidebar');
    const sendButton = document.getElementById('sendMessage');
    const chatInput = document.getElementById('chatInput');

    console.log("Send Button Element:", sendButton);
    console.log("Chat Input Element:", chatInput);

    if (!chatToggle || !chatSidebar || !sendButton || !chatInput) {
        console.error("Chat elements not found! Cannot attach listeners.");
        return;
    }
    console.log("Chat elements found. Proceeding to attach listeners.");

    chatToggle.addEventListener('click', () => {
        const pdfViewer = document.getElementById('pdfViewer');
        chatSidebar.classList.toggle('d-none');
        if (chatSidebar.classList.contains('d-none')) {
            pdfViewer.classList.remove('col-md-9');
            pdfViewer.classList.add('col-md-12');
        } else {
            pdfViewer.classList.remove('col-md-12');
            pdfViewer.classList.add('col-md-9');
        }

        if (currentPdfDoc) {
            setTimeout(() => {
                renderPage(currentPdfDoc, currentPage);
            }, 250); // Small delay to allow CSS transitions
        }
    });

    console.log("Attaching event listener to send button...");
    sendButton.addEventListener('click', sendChatMessage);

    console.log("Attaching event listener to chat input (keypress)...");
    chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            console.log("Enter key pressed in chat input.");
            sendChatMessage();
        }
    });

    initChatWithHistory();

    console.log("Chat setup finished.");
}

async function sendChatMessage() {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    const bookId = document.getElementById('bookId').value;

    if (message) {
        addMessage(message, 'user');
        input.value = '';

        const typingIndicator = document.createElement('div');
        typingIndicator.className = 'ai-message typing';
        typingIndicator.innerHTML = '<p><i class="fas fa-spinner fa-spin me-2"></i>Thinking...</p>';
        document.getElementById('chatMessages').appendChild(typingIndicator);
        document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;

        try {
            const context = await getCurrentPageText();

            const response = await fetch('/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    message: message,
                    currentPage: currentPage,
                    pdfContext: context,
                    bookId: bookId
                })
            });

            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.status);
            }

            const data = await response.json();

            document.querySelector('.typing')?.remove();

            addMessage(data.result.output.content, 'ai');
        } catch (error) {
            console.error("Error sending message:", error);
            document.querySelector('.typing')?.remove();
            addMessage("Sorry, I couldn't process your request. Please try again later. The local AI model might need more time to respond or may not be running.", 'ai');
        }
    }
}

function addMessage(text, sender) {
    const messagesDiv = document.getElementById('chatMessages');
    const messageDiv = document.createElement('div');
    messageDiv.className = `${sender}-message`;
    messageDiv.innerHTML = `<p>${text}</p>`;
    messagesDiv.appendChild(messageDiv);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

async function loadChatHistory(bookId) {
    try {
        const response = await fetch(`/api/chat/${bookId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch chat history');
        }

        const chatHistory = await response.json();
        const messagesDiv = document.getElementById('chatMessages');

        messagesDiv.innerHTML = '';

        chatHistory.forEach(msg => {
            addMessage(msg.content, msg.aiGenerated ? 'ai' : 'user');
        });

        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    } catch (error) {
        console.error("Error loading chat history:", error);
    }
}

function initChatWithHistory() {
    const bookId = document.getElementById('bookId')?.value;
    if (bookId) {
        loadChatHistory(bookId);
    }
}
