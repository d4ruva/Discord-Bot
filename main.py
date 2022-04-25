'''
Author: Dhruva
Date: 22-04-2022
'''

import discord
import os
import json
from dotenv import load_dotenv

client = discord.Client()

def append_data(dataToUpload):
    with open('static.json', 'r+') as file:
        data = json.load(file)
        data['messages'].append(dataToUpload)
        file.seek(0)
        json.dump(data, file, indent=4)

@client.event
async def on_ready():
    print(f'Logged in as {client.user}')

@client.event
async def on_message(message):

    # Print the message
    print(f'[{message.author}]: {message.content}')

    # Check if the message is from self
    if message.author == client.user:
        return
        
    for _ in responses['messages']:
        if message.content == _ ['message']:
            await message.reply(_ ['response'])

    # Ignore if not command
    if not str(message.content).startswith("!"):
        return

    else:
        #Remove the command prefix
        message.content = str(message.content)[1:]

    
    if message.content.split(" ")[0] == "uploadReply":
        command = message.content.split(" ")
        append_data({"message": command[1], "response": command[2]})
        await message.channel.send("uploaded successfully")

    for _ in responses['embeds']:
        if message.content == _['title']:
            embed = discord.Embed(title= _['response']['title'], description=_['response']['description'])
            
            for field in _['response']['fields']:
                embed.add_field(name=field['name'], value=field['value'])
                
            await message.channel.send(content=None, embed=embed)


if __name__ == "__main__":
    try:
        responses = json.load(open("static.json"))
        load_dotenv()
        client.run(os.getenv("TOKEN"))

    except Exception as e:
        print(e)
        