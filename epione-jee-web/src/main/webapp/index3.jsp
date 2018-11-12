<!DOCTYPE html>
<html>
    <head>
        <title>Chat users</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <div class="table">
            <div>
                <span>Your Username:</span>
                <input id="username" placeholder="John Doe"/>
            </div>
            <button onclick="connect();return false;" class="button">Connect</button>
        </div>
        <div class="table">
            <div>Users</div>
            <div id="usersList" ></div>
        </div>
        <div id="globalMessage"></div>
        <div>You are talking to: <span id="selectedUser"></span></div>
        <div id="messages"></div>
        <div class="table">
            <div>
                <span>Message:</span>
                <input type="text" name="message" id="message"/>
            </div>
            <button onclick="send();return false;" class="button">Send</button>
        </div>
        <script type="text/javascript">
            var messages = new Map();
            var username;
            var contacts;
            var ws;
            var selectedUser;
            connect = function() {
                username = document.getElementById('username').value;
                document.cookie = "username=" + username;
                var webSocketUrl = 'ws://localhost:18080/epione-jee-web/chatUsers';
                ws = new WebSocket(webSocketUrl);
                ws.onopen = function() {
                    globalMessage('info', 'Connection opened!');
                };
                ws.onmessage = function(event) {
                    var message = JSON.parse(event.data);
                    if (message.type && message.type === 'users') {
                        updateUsers(message.content);
                    } else {
                        appendMessage(message.content, message.fromUser);
                    }
                };
                ws.onclose = function() {
                    globalMessage('warn', 'WebSocket closed');
                };
                ws.onerror = function(err) {
                    globalMessage('warn', err);
                };
            };
            globalMessage = function(type, text) {
                document.getElementById('globalMessage').innerHTML = '<div class="' + type + '">' + text + '</div>';
            };
            appendMessage = function(content, user) {
                if (!messages.get(user)) {
                    newUser(user);
                }
                var text = user+': '+content;
                if (selectedUser === user) {
                    document.getElementById('messages').innerHTML += messageDiv(text);
                } else {
                    messages.get(user).messages.push(text);
                    messages.get(user).unread++;
                    updateUser(user);
                }
            };
            messageDiv = function(text) {
                return '<div class="message">' + text + '</div>';
            };
            updateUser = function(user) {
                document.getElementById('user-' + user).innerHTML = user+' ('+messages.get(user).unread+')';
            };
            updateUsers = function(users) {
                console.log(users);
                users.forEach(function(user) {
                    if (!document.getElementById('user-'+user)) {
                        newUser(user);
                    }
                });
            };
            newUser = function(user) {
                messages.set(user, {'unread':0,'messages':[]});
                document.getElementById('usersList').innerHTML += '<a href="#" id="user-'+user+'" onclick="selectUser(\''+user+'\');return false;">'+user+'</a>';
            };
            selectUser = function(user) {
                selectedUser = user;
                document.getElementById('selectedUser').innerHTML = user;
                var messagesBox = document.getElementById('messages');
                messagesBox.innerHTML = "";
                messages.get(user).messages.forEach(function(message) {
                    messagesBox.innerHTML += messageDiv(message);
                });
                messages.get(user).unread = 0;
                updateUser(user);
            };
            send = function() {
                var message = document.getElementById('message').value;
                ws.send(JSON.stringify({'content':message,'fromUser':username,'toUser':selectedUser}));
                var text = username + ': ' + message;
                messages.get(selectedUser).messages.push(text);
                document.getElementById('messages').innerHTML += messageDiv(text);
            };
        </script>
    </body>
</html>