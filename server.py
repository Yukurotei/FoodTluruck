from transformers import AutoModelForCausalLM, AutoTokenizer
from flask import Flask, request, jsonify
import torch

app = Flask(__name__)

print("Loading DistilGPT-2...")
tokenizer = AutoTokenizer.from_pretrained("distilgpt2")
model = AutoModelForCausalLM.from_pretrained("distilgpt2")
print("Loaded DistilGPT-2!")

@app.route("/review", methods=["POST"])
def review():
    data = request.get_json()
    prompt = data.get("prompt", "")

    inputs = tokenizer(prompt, return_tensors="pt")

    outputs = model.generate(
    inputs["input_ids"],
    max_new_tokens=24,         
    do_sample=True,
    temperature=0.55,          
    top_p=0.85,
    repetition_penalty=1.7,    
    eos_token_id=tokenizer.eos_token_id
)


    text = tokenizer.decode(outputs[0], skip_special_tokens=True)
    # Return ONLY the continuation
    text = text[len(prompt):].strip()

    return jsonify({"review": text})


app.run(port=5005)
