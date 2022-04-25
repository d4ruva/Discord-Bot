import json 

responses = json.load(open("response.json"))


for _ in responses['messages']:
    print(_['message'])

message = input("Enter message: ")
response = input("Enter message: ")

new_data = {
    "message": message,
    "response": response
}

def append_data(dataToUpload):
    with open('response.json', 'r+') as file:
        data = json.load(file)

        data['messages'].append(dataToUpload)

        file.seek(0)

        json.dump(data, file, indent=4)


append_data(new_data)
print(responses)