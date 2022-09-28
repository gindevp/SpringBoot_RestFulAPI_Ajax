function showAllClass(){
$.ajax({
    url:"http://localhost:8080/api/customers",
    success: function (data){
        console.log(data)
        let content=""
        for (let i = 0; i < data.length; i++) {
            content+=`<tr><td>${data[i].id}</td>
        <td>${data[i].firstName}</td>
        <td>${data[i].lastName}</td>
        <td><a href="${data[i].id}" onclick="deleteClass(this)">Xoa</a></td>
        <td><a href="${data[i].id}" onclick="viewEdit(this)">Edit</a></td></tr>`;
        }
        document.getElementById("tbody").innerHTML= content;
    }
})
}
showAllClass();
function deleteClass(element) {
    // lay id
    let id = element.getAttribute("href");
    //goi API phia backend
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/customers/delete/"+ id,
        success: function (data){
            console.log("Xoa thanh cong id " + id);
            //xoa the
            // thay doi giao dien
            showAllClass();
        }
    })
    //chan su kien mac dinh cua the
    event.preventDefault();
}

function create(){
    let name=document.getElementById("first_name").value;
    let lname= document.getElementById("last_name").value;
    let ob={
        firstName: name,
        lastName:lname
    }
    $.ajax({
        headers:{
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(ob),
        url: "http://localhost:8080/api/customers",
        success: function (){
            showAllClass();
        }
    })
}
function viewEdit(element){
let id= element.getAttribute("href")

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/customers/"+ id,

        success: function (data){
            console.log("show id " + id);
           document.getElementById("first_name").value=data.firstName
            let b=document.getElementById("last_name")
                b.value=data.lastName
            document.getElementById("edit").innerHTML=`<button onclick="edit(${id})">Edit</button>`
            //xoa the
            // thay doi giao dien
        }
    })
    event.preventDefault();
}
function edit(id){
    let name=document.getElementById("first_name").value;
    let lname= document.getElementById("last_name").value;
    let ob={
        firstName: name,
        lastName:lname
    }
    $.ajax({
        "url": "http://localhost:8080/api/customers/edit/"+id,
        "method": "PUT",
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify(ob),
        success: function (){
            console.log(id)
            showAllClass();
        }
    })
$
}