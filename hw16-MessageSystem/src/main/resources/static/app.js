let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    console.log(connected);
    $("#saveButton").prop("disabled", !connected);
    console.log(connected);
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/userList', (usersJson) => showUsers(JSON.parse(usersJson.body)))
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const saveUser = () => {
    let user = new Object();
    user.name = document.getElementById("name-input").value;
    user.age = document.getElementById("age-input").value;
    user.address = document.getElementById("address-input").value;
    user.phone = document.getElementById("phone-input").value;
    stompClient.send("/app/newUser", {}, JSON.stringify(user));
}

const showUsers = (allUsers) => {
    $("#usersTable").html("");
    allUsers.forEach(function(entry) {
        let phone = entry.phones[0].number;
        $("#usersTable").append("<tr>" +
            "<td>" + entry.name + "</td>" +
            "<td>" + entry.age + "</td>" +
            "<td>" + entry.address + "</td>" +
            "<td>" + phone + "</td>" +
            "</tr>");
    });
}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#saveButton").click(saveUser);
});
