let uniqId = 1;

function cartStorage(){
	debugger;
	let detailsDiv = document.getElementById('detail').children;
	let pid = detailsDiv[0].value;
	let pname = detailsDiv[1].textContent;
	let price = detailsDiv[2].textContent;
	let desc = detailsDiv[3].textContent;
	let qtyDiv = document.getElementById('qtyDiv').children;
	let qty = qtyDiv[1].value;
	let sizeList = document.getElementsByName('size');
	let size = null;
	
	sizeList.forEach(val => {
		if(val.checked==true) {
			size = val.value;
		}
	});

	if (size != null){
		let cart = localStorage.getItem("cart");
		if (cart == null){
			let products = [];
			let product = {
				id: uniqId,
				productId: pid,
				productName: pname,
				description: desc,
				price: price,
				quantity: qty,
				size: size
			};
			products.push(product);
			localStorage.setItem("cart", JSON.stringify(products));
		} else{
			let pcart = JSON.parse(cart);
			let oldProduct = pcart.find((item) => item.productId == pid && item.size == size)
			if (oldProduct){
				oldProduct.quantity = +oldProduct.quantity + +qty;
				pcart.map((item) => {
					if (item.productId == oldProduct.productId){
						item.quantity = oldProduct.quantity;
					}
				});
				localStorage.setItem("cart", JSON.stringify(pcart));
			
			} else{
				pcart.map((item) => {
					uniqId= item.id + 1;
				});
				let product = {
					id: uniqId,
					productId: pid,
					productName: pname,
					description: desc,
					price: price,
					quantity: qty,
					size: size
				};
				pcart.push(product);
				localStorage.setItem("cart", JSON.stringify(pcart));
			}
		}
		viewStorage();
		
	} else{
		//document.getElementById('sizeerror').textContent = "Select any Size";
		alert('Select any size');
	}

}	

let cartadd = document.getElementsByClassName('cartt text-danger');
let cartDiv = document.getElementsByClassName('cartproduct');

function viewStorage(){
	let cartitem = localStorage.getItem("cart");
	
  	if (cartitem != null && cartitem.length > 2){
		let cartdata = JSON.parse(cartitem);
		
		if (cartdata.length == undefined){
			cartadd[0].innerText = "(0)";
		} else {
			cartadd[0].innerText = "(" + cartdata.length+ ")";
		}
		
	
		let table = `
		<table class = 'table'>
			<thead class = 'thead-light'>
				<tr>
					<th>S.No.</th>
					<th>Item Name</th>
					<th>Description</th>
					<th>Price</th>
					<th>Size</th>
					<th>Quantity</th>
					<th>Total Price</th>
					<th>Action</th>
				</tr>
			</thead>
		
			`;
		
			let totalPrice = 0;
			
			cartdata.map((item) => {
			
				
				table += `
					<tr>
						<td>${item.id}</td>
						<td>${item.productName}</td>
						<td>${item.description}</td>
						<td>${item.price}</td>
						<td>${item.size}</td>
						<td>${item.quantity}</td>
						<td>${item.quantity * item.price}</td>
						<td><button onclick = 'deleteItem(${item.id})' class = 'btn btn-danger btn-sm'>Remove</button></td>
					</tr>
					`
					
					totalPrice += item.quantity * item.price;
			})
		
			table = table + `
				<tr><td colspan = '8' class = 'totalp'>Total Price : ${totalPrice}</td></tr>
			</table>`
		
			cartDiv[0].innerHTML = table;
  	}
  	else{
		cartadd[0].innerText = "(0)";
		cartDiv[0].innerText = "Cart is Empty, No Item Found";
		cartDiv[0].style.fontWeight = "bold";
		$('#place').hide();
		$('#confirm').hide();
  	}
  	
		
}	
	
window.onload = function () {
  viewStorage(); 
}

function deleteItem(id){
	let cart = JSON.parse(localStorage.getItem('cart'));
	let newCart = cart.filter((item) => item.id != id);
	
	localStorage.setItem('cart',JSON.stringify(newCart));
	
	viewStorage();
	
}
  
$(document).ready(function() {
	
	$("#login").click(function(e) {
		debugger;
		e.preventDefault();
		
		let userName = document.getElementById('userName').value;
		let password = document.getElementById('userPassword').value;
		
		$.ajax({
			url: 'authenticate',
			type: 'POST',
			data: JSON.stringify({ userName: userName, userPassword: password }),
			dataType: 'json',
			headers: {
				"Accept": "application/json",
				"Content-type": "application/json"
			},
			success: function(data) {
				console.log("success");
				redirect(data.user.role[0].roleName);
			},
			error: function(data) {
				console.log("error");
			}
		});
		return false;
	});
});

function redirect(role){
	debugger;
	let url;
	
	if (role == "Admin"){
		url = "/admin";
		
	} else{
		url = "/home";
	}
	localStorage.setItem("status", "loggedIn");
	localStorage.setItem("role", role);
	window.location.href = url;
}

$('#out').click(function(e){
	debugger;
	e.preventDefault();
	localStorage.setItem("status", "loggedOut");
	window.location.href = "/logout";
	
});


$('#adminout').click(function(e){
	debugger;
	e.preventDefault();
	localStorage.setItem("status", "loggedOut");
	window.location.href = "/logout";
	
});

if (localStorage.getItem('status') == "loggedIn"){
	if (localStorage.getItem('role') == "Admin"){
		$('#adminlog').hide();
		$('#adminout').show();
		
	} else{
		$('#log').hide();
		$('#reg').hide();
		$('#out').show();
	}
	
} else{
	if (localStorage.getItem('role') == "Admin"){
		$('#adminlog').show();
		$('#adminout').hide();
		
	} else{
		$('#log').show();
		$('#reg').show();
		$('#out').hide();
	}	
}
	
$('.main-carousel').flickity({
  	cellAlign: 'left',
  	wrapAround:true,
  	freeScroll:true
});

function confirmOrder(){
	window.location.href = "/confirmorder";
	localStorage.removeItem("cart");
}

    