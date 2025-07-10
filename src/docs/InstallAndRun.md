# Ollama - ინსტალაცია და გამოყენება


## ინსტალაცია
გადმოწერა [ოფიციალური საიტიდან](https://ollama.com/download)
### macOS-ზე
```bash
# Homebrew-ის გამოყენებით
brew install ollama

curl -fsSL https://ollama.com/install.sh | sh
```

### Windows-ზე
1. გადმოიწერეთ `ollama-setup.exe` ოფიციალური საიტიდან
2. დაამატეთ PATH-ში ollama.exe-ს მდებარეობა

## მოდელების ჩამოტვირთვა

### მოდელის ჩამოთვირთვა:
```bash
ollama pull <Model name>
#მაგალითად
ollama pull Tinnyllama

```

## ძირითადი ბრძანებები

### მოდელის გაშვება:
```bash
# ინტერაქციული რეჟიმი
ollama run llama2

# კონკრეტული შეკითხვა
ollama run llama2 "Explain artificial intelligence in simple terms"

```

### მოდელების მენეჯმენტი:
```bash
# ყველა ჩამოტვირთული მოდელის სია
ollama list

# მოდელის წაშლა
ollama rm Tinnyllama

# მოდელის ინფორმაცია
ollama show Tinnyllama

# მოდელის ჩამოტვირთვა ფონში
ollama pull Tinnyllama &
```

### სერვისის მენეჯმენტი:
```bash
# სერვისის დაწყება
ollama serve #ლინუქსე აუცილებელი ქომანდი, ვინდოუსზე კი არა რადგან ოლლამა ფონურ რეჟიმში იქნე გაშვებული ისედაც

# სერვისის შეჩერება
# Linux-ზე
sudo systemctl stop ollama

# macOS-ზე
brew services stop ollama
```


## სხვადასხვა ინტეგრაცია

### VS Code Extension
1. დააინსტალირეთ "Ollama" extension VS Code-ში
2. გახსენით Command Palette (`Ctrl+Shift+P`)
3. ჩაწერეთ "Ollama" და გამოიყენეთ



## კონფიგურაცია

### ძირითადი პარამეტრები:
```bash
# მოდელის ტემპერატურა🌡️ (შემოქმედებითობა)
ollama run llama2 --temperature 0.7

# ტოკენების მაქს რაოდენობა
ollama run llama2 --num-predict 1000

# კონტექსტის სიგრძე
ollama run llama2 --num-ctx 4096
```

### Modelfile-ის შექმნა:
```
FROM llama2

# სისტემური პრომპტი
SYSTEM "You are a helpful assistant that speaks Georgian."

# პარამეტრები
PARAMETER temperature 0.8
PARAMETER num_predict 2000
PARAMETER num_ctx 4096

# ფრაზის შაბლონი
TEMPLATE """{{ if .System }}<|system|>
{{ .System }}<|end|>
{{ end }}{{ if .Prompt }}<|user|>
{{ .Prompt }}<|end|>
{{ end }}<|assistant|>
{{ .Response }}<|end|>
"""
```

მოდელის შექმნა:
```bash
ollama create my-georgian-model -f ./Modelfile
ollama run my-georgian-model
```


## ალტერნატივები

- **LM Studio**: GUI ინტერფეისი
- **GPT4All**: მსგავსი ფუნქციონალი
- **Llama.cpp**: უფრო ტექნიკური მიდგომა
- **Hugging Face Transformers**: Python-ბეისდ ალტერნატივა

## დამატებითი რესურსები

- [ოფიციალური დოკუმენტაცია](https://github.com/ollama/ollama)
- [მოდელების ბიბლიოთეკა](https://ollama.com/library)
- [Community Discord](https://discord.gg/ollama)
- [GitHub Issues](https://github.com/ollama/ollama/issues)

---

**შენიშვნა**: Ollama აქტიურად იყმენება და ახალი ფუნქციები ემატება რეგულარულად. ყოველთვის შეამოწმეთ უახლესი ვერსია და დოკუმენტაცია.