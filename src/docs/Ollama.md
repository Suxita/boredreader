# 🦙 Ollama and Spring AI



[Ollama](https://ollama.com/) is an open-source framework for running large language models (LLMs) locally on your machine. It provides:

- A local API server for LLM interactions
- Support for running on CPU/GPU (depending on model size and hardware)
- Access to various open-source models

 
## Available Models

Below is a comprehensive list of models you can run with Ollama:

| Model | Parameters | Size | Download Command |
|-------|------------|------|-----------------|
| **Gemma 3** | 1B | 815MB | `ollama run gemma3:1b` |
| **Gemma 3** | 4B | 3.3GB | `ollama run gemma3` |
| **Gemma 3** | 12B | 8.1GB | `ollama run gemma3:12b` |
| **Gemma 3** | 27B | 17GB | `ollama run gemma3:27b` |
| **QwQ** | 32B | 20GB | `ollama run qwq` |
| **DeepSeek-R1** | 7B | 4.7GB | `ollama run deepseek-r1` |
| **DeepSeek-R1** | 671B | 404GB | `ollama run deepseek-r1:671b` |
| **Llama 3.3** | 70B | 43GB | `ollama run llama:3.3` |
| **Llama 3.2** | 3B | 2.0GB | `ollama run llama:3.2` |
| **Llama 3.2** | 1B | 1.3GB | `ollama run llama:3.2:1b` |
| **Llama 3.2 Vision** | 11B | 7.9GB | `ollama run llama:3.2-vision` |
| **Llama 3.2 Vision** | 90B | 55GB | `ollama run llama:3.2-vision:90b` |
| **Llama 3.1** | 8B | 4.7GB | `ollama run llama:3.1` |
| **Llama 3.1** | 405B | 231GB | `ollama run llama:3.1:405b` |
| **Phi 4** | 14B | 9.1GB | `ollama run phi4` |
| **Phi 4 Mini** | 3.8B | 2.5GB | `ollama run phi4-mini` |
| **Mistral** | 7B | 4.1GB | `ollama run mistral` |
| **Moondream 2** | 1.4B | 829MB | `ollama run moondream` |
| **Neural Chat** | 7B | 4.1GB | `ollama run neural-chat` |
| **Starling** | 7B | 4.1GB | `ollama run starling-lm` |
| **Code Llama** | 7B | 3.8GB | `ollama run codellama` |
| **Llama 2 Uncensored** | 7B | 3.8GB | `ollama run llama2-uncensored` |
| **LLaVA** | 7B | 4.5GB | `ollama run llava` |
| **Granite-3.2** | 8B | 4.9GB | `ollama run granite3.2` |
| **TinyLlama** | 1.1B | ~1GB | `ollama run tinyllama` |

## Hardware Requirements

### GPU Memory (VRAM) Requirements

Larger models (higher parameter count) require more GPU memory and computational power:

- **Rule of Thumb**: ~1.2-1.5GB of VRAM per billion parameters for base inference
- **Example**: A 7B parameter model needs roughly 8-10GB VRAM minimum


## Project Implementation

In my project, i tested two models:

1. **Mistral** (default model when integrating in spring project)
    - Parameters: 7B
    - Size: 4.1GB
    - Good balance of performance and resource requirements

2. **TinyLlama**
    - Parameters: 1.1B
    - Size: 600MB-1GB
    - good for general testing purposes but nothing more


***if u want to use other model enter it in properties***
### System Memory (RAM) Requirements

Depending on model size:

| Model Size | RAM Recommendation |
|------------|-------------------|
| Small (1-7B) | 8GB minimum |
| Medium (7-13B) | 16GB+ recommended |
| Large (13B+) | 32GB+ high-end |


## Spring AI Integration

Spring AI is a Java library that provides integration between LLMs and the Spring ecosystem with these features:

### Key Features

1. **Unified API**
    - Consistent interface for different AI models (OpenAI, Anthropic, local models)

2. **Spring Integration**
    - Seamless integration with Spring Boot and related frameworks

3. **Prompt Engineering Support**
    - Tools for templating, managing, and optimizing prompts

4. **Vector Database Support**
    - Embedding operations and integration for retrieval-augmented generation (RAG)

5. **Model Abstraction**
    - Switch between models without changing application code

6. **Streaming Responses**
    - Handle streaming responses from AI models efficiently

***Useful links for more detailed information***
1) [ollamas github](https://github.com/ollama/ollama)
2) [Spring ai](https://spring.io/projects/spring-ai)