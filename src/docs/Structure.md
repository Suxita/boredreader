##  🗃️Project Structure

```

                
├── src/
│   └── main/
│      ├── java/
│      │   └── ge/
│      │       └── tsu/
│      │           └── boredreader/
│      │               ├── BoredreaderApplication.java    
│      │               ├── OllamaConfig.java               Spring AI/Ollama Configuration
│      │               ├── Controller/                  
│      │               │   ├── AboutController.java        Maps /about
│      │               │   ├── CatalogController.java      Maps /catalog, handles book listing
│      │               │   ├── ChatController.java         Maps /api/chat, handles AI chat API
│      │               │   ├── HomeController.java         Maps /home
│      │               │   ├── ImageController.java        serves images
│      │               │   ├── PdfController.java          serves PDFs
│      │               │   └── ReaderController.java       renders book reader page
│      │               ├── entity/                       
│      │               │   ├── Book.java                   Represents a book
│      │               │   ├── ChatMessage.java            Represents a chat message
│      │               │   └── User.java                   Represents a user
│      │               ├── service/   
│      │               │   ├── BookSevice.java             Add Sample Books 
│      │               └── repository/                 
│      │                   ├── BookRepository.java         Repository for Book entity
│      │                   └── ChatMessageRepository.java  Repository for ChatMessage entity
│      └── resources/
│          ├── application.properties                      Application configuration
│          ├── static/                                    
│          │   ├── assets/       
│          │   │   ├── covers/                             book covers folder           
│          │   │   ├── images/                             images used on pages folder               
│          │   │   └── pdfs/                               pdfs folder              
│          │   │       
│          │   ├── css/                                    stylesheet folder
│          │   └── js/                                     
│          │       └── app.js                              The frontend JS snippet would likely be here or similar
│          └── templates/        
│              ├── fragments/    
│              │    ├── footer.html                        footer for layout
│              │    └── header.html                        header fot layout
│              ├── about.html                              about page
│              ├── catalog.html                            book catalog
│              ├── error.html                              error template 
│              ├── index.html                              the home page
│              ├── reader.html                             book reader page
│              └── layout.html                             Page Template
│             
       
└── pom.xml                                                DEPENDENCIES and more 
``` 