import React, { useEffect, useState } from "react";
import config from "../../config/config";
import { Search } from "lucide-react";
import "./ChatPage.css";
import { useUser } from '../../UserContext'; 

const ChatPage = () => {
  const { userEmail, userId } = useUser();
  const [message, setMessage] = useState("");
  const [selectedChat, setSelectedChat] = useState(null);
  const [chats, setChats] = useState([]);

  const transformData = (data) => {
    return data.map(item => {
      const formattedTime = new Date(item.dateTime).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit', hour12: true});
  
      return {
        ...item,
        time: formattedTime,
        lastMessage: "", 
        messages: [] 
      };
    });
  };

  useEffect(() => {
    const fetchChats = async () => {
      const response = await fetch(`${config.USER_DASH}/group/user/${userId}`); 
      let data = await response.json();
      console.log('data', data);
      data = transformData(data);
      setChats(data); 
    };

    fetchChats();
  }, []);

  const handleSendMessage = (e) => {
    e.preventDefault();

    if (message.trim() && selectedChat) {
      const newMessage = {
        id: Date.now(),
        sender: "You",
        text: message,
        time: new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })
      };

      setChats((prevChats) =>
        prevChats.map((chat) =>
          chat.id === selectedChat.id
            ? {
                ...chat,
                messages: [...chat.messages, newMessage],
                lastMessage: newMessage.text,
                time: newMessage.time
              }
            : chat
        )
      );

      setSelectedChat((prevSelectedChat) => ({
        ...prevSelectedChat,
        messages: [...prevSelectedChat.messages, newMessage]
      }));

      setMessage("");

      setTimeout(() => {
        const chatContainer = document.querySelector(".messages-container");
        if (chatContainer) chatContainer.scrollTop = chatContainer.scrollHeight;
      }, 100);
    }
  };

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (file && selectedChat) {
      const fileUrl = URL.createObjectURL(file); 
      const newMessage = {
        id: Date.now(),
        sender: "You",
        text: `Uploaded file: `,
        fileName: file.name,
        fileUrl: fileUrl,
        time: new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })
      };

      setChats((prevChats) =>
        prevChats.map((chat) =>
          chat.id === selectedChat.id
            ? {
                ...chat,
                messages: [...chat.messages, newMessage],
                lastMessage: `Uploaded: ${file.name}`,
                time: newMessage.time
              }
            : chat
        )
      );

      setSelectedChat((prevSelectedChat) => ({
        ...prevSelectedChat,
        messages: [...prevSelectedChat.messages, newMessage]
      }));

      setTimeout(() => {
        const chatContainer = document.querySelector(".messages-container");
        if (chatContainer) chatContainer.scrollTop = chatContainer.scrollHeight;
      }, 100);
    }
  };

  return (
    <div className="chat-layout">
      {/* Left Sidebar */}
      <div className="chat-sidebar">
        <div className="search-container">
          <Search className="search-icon" />
          <input
            type="text"
            placeholder="Search for people..."
            className="search-input"
          />
        </div>

        <div className="recent-chats">
          <h2>Recent Chats</h2>
          {chats.map((chat) => (
            <div
              key={chat.id}
              className={`chat-item ${selectedChat?.id === chat.id ? "active" : ""}`}
              onClick={() => setSelectedChat(chat)}
            >
              <div className="chat-avatar">{chat.name[0]}</div>
              <div className="chat-item-info">
                <div className="chat-item-name">{chat.name}</div>
                <div className="chat-item-message">{chat.lastMessage}</div>
              </div>
              <div className="chat-item-time">{chat.time}</div>
            </div>
          ))}
        </div>
      </div>

      {/* Main Chat Area */}
      <div className="chat-main">
        {selectedChat ? (
          <>
            <div className="chat-header">
              <h2>Chat with {selectedChat.name}</h2>
            </div>

            <div className="messages-container">
              {selectedChat.messages.map((msg) => (
                <div
                  key={msg.id}
                  className={`message ${msg.sender === "You" ? "sent" : "received"}`}
                >
                  <div className="message-content">
                    <div className="message-sender">{msg.sender}</div>
                    {msg.fileName ? (
                      <>
                        <div className="message-text">
                          {msg.text}{" "}
                          <a href={msg.fileUrl} download={msg.fileName}>
                            {msg.fileName}
                          </a>
                        </div>
                      </>
                    ) : (
                      <div className="message-text">{msg.text}</div>
                    )}
                    <div className="message-time">{msg.time}</div>
                  </div>
                </div>
              ))}
            </div>

            <div className="message-input-container">
              <input
                type="file"
                id="fileUpload"
                onChange={handleFileUpload}
                style={{ display: "none" }}
              />
              <label htmlFor="fileUpload" className="file-upload-button">
                📎
              </label>

              <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type a message..."
                className="message-input"
              />
              <button className="send-button" onClick={handleSendMessage}>
                Send
              </button>
            </div>
          </>
        ) : (
          <div className="no-chat-selected">Select a chat to start messaging</div>
        )}
      </div>
    </div>
  );
};

export default ChatPage;