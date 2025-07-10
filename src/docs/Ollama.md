# 🦙 Ollama და Spring AI

[ინსტალაციის და მასთან მუშაობის გაიდი](src/docs/InstallAndRun.md)
[Ollama](https://ollama.com/) არის open source ჩარჩო დიდი ენობრივი მოდელების (LLMs) ლოკალურად, თქვენს მანქანაზე გასაშვებად. ის უზრუნველყოფს:

- ლოკალურ API სერვერს LLM ინტერაქციებისთვის
- მხარდაჭერას CPU/GPU-ზე გასაშვებად (დამოკიდებულია მოდელის ზომასა და ტექნიკურ მონაცემებზე)
- წვდომას სხვადასხვა open source მოდელებთან
- იგი გაძლევთ საშუალებას შექმნათ თქვენი მოდელები ან დაასწავლოთ არსებული მოდელის ბაზზაზე.

## ხელმისაწვდომი მოდელები

ქვემოთ მოცემულია ყველა მოდელის ჩამონათვალი, რომლებიც შეგიძლიათ გაუშვათ Ollama-ს გამოყენებთ:

| მოდელი | პარამეტრები | ზომა | ჩამოტვირთვის ბრძანება |
|--------|-------------|------|---------------------|
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

## ტექნიკური მოთხოვნები

### GPU მეხსიერების (VRAM) მოთხოვნები

დიდი მოდელები (მაღალი პარამეტრების რაოდენობა) მოითხოვს მეტ GPU მეხსიერებასა და გამოთვლით უნარს:

- **ოქროს წესი**: ~1.2-1.5GB VRAM მილიარდ პარამეტრზე ძირითადი ინფერენციისთვის
- **მაგალითი**: 7B პარამეტრიან მოდელს სჭირდება რაღაც 8-10GB VRAM მინიმუმ

## პროექტის განხორციელება

ჩემს პროექტში, ვტესტე ორი მოდელი:

1. **Mistral** (ნაგულისხმევი მოდელი spring პროექტში ინტეგრირებისას)
   - პარამეტრები: 7B
   - ზომა: 4.1GB
   - კარგი ბალანსი წარმადობისა და რესურსული მოთხოვნებისთვის

2. **TinyLlama**
   - პარამეტრები: 1.1B
   - ზომა: 600MB-1GB
   - კარგია ზოგადი ტესტირებისთვის, მაგრამ მეტი არაფერი

***თუ სხვა მოდელის გამოყენება გინდათ, მიუთითეთ იგი properties-ში***

### სისტემის მეხსიერების (RAM) მოთხოვნები

მოდელის ზომის მიხედვით:

| მოდელის ზომა | RAM რეკომენდაცია |
|-------------|------------------|
| პატარა (1-7B) | 8GB მინიმუმ |
| საშუალო (7-13B) | 16GB+ რეკომენდებული |
| დიდი (13B+) | 32GB+ მაღალი დონე |

## Spring AI ინტეგრაცია

Spring AI არის Java ბიბლიოთეკა, რომელიც უზრუნველყოფს ინტეგრაციას LLM-ებსა და Spring ეკოსისტემას შორის შემდეგი თვისებებით:

### ძირითადი თვისებები

1. **ერთიანი API**
   - თანმიმდევრული ინტერფეისი სხვადასხვა AI მოდელებისთვის (OpenAI, Anthropic, ლოკალური მოდელები)

2. **Spring ინტეგრაცია**
   - უწყვეტი ინტეგრაცია Spring Boot-სა და მასთან დაკავშირებულ ჩარჩოებთან

3. **Prompt Engineering მხარდაჭერა**
   - ინსტრუმენტები შაბლონების, მართვისა და prompt-ების ოპტიმიზაციისთვის

4. **Vector Database მხარდაჭერა**
   - Embedding ოპერაციები და ინტეგრაცია retrieval-augmented generation (RAG)-ისთვის

5. **მოდელის აბსტრაქცია**
   - მოდელებს შორის გადართვა აპლიკაციის კოდის შეცვლის გარეშე

6. **Streaming პასუხები**
   - AI მოდელების streaming პასუხების ეფექტური მართვა

***სასარგებლო ბმულები დეტალური ინფორმაციისთვის***
1) [ollama-ს github](https://github.com/ollama/ollama)
2) [Spring ai](https://spring.io/projects/spring-ai)

### Ps
დავივიწყე დავამატო, რომ შეგიძლიათ ლოკალური llm-ების ტრენინგი თქვენი საკუთარი მონაცემებით, შეიძლება მსახიობი და გამოწვევადი იყოს, შესაძლებლობა არსებობს 