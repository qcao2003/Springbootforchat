
var connection;
function openSocket(){
    if ("WebSocket" in window){
        connection = new WebSocket("ws://localhost:8080/test");
        connection.onopen = function () {
            // setMessageInnerHTML("WebSocket连接成功");
        }
        connection.onerror = function (){
            // setMessageInnerHTML("WebSocket连接发生错误");
        }
        connection.onclose = function (){
            // setMessageInnerHTML("WebSocket连接关闭");
        }
        connection.onmessage = function (event){
            /*发生事件后的data*/
            setMessageInnerHTML(event.data);
        }
    }
}

//发送数据给服务器，获得id是message的值
function sendMessage() {
    /*把message1数据发送到后台*/
    connection.send(document.getElementById("message1").value);
    // connection.send(document.getElementById("message2").value);
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

