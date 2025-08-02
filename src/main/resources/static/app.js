let products = [];
let cart = JSON.parse(localStorage.getItem("cart")) || [];

document.addEventListener("DOMContentLoaded", () => {
    const path = window.location.pathname;

    if (path.includes("index.html") || path === "/" || path.endsWith("AlienKart") || path.endsWith("AlienKart/")) {
        fetchProducts();
    } else if (path.includes("orders.html")) {
        loadOrders();
    }
});

// ---------------- PRODUCT FETCH ----------------

function fetchProducts() {
    fetch("http://localhost:8080/products") // ✅ corrected endpoint
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch products");
            return res.json();
        })
        .then(data => {
            products = data;
            displayProducts();
        })
        .catch(err => {
            console.error("🚨 Product fetch error:", err);
            const container = document.getElementById("product-list");
            if (container) container.innerHTML = "<p>⚠️ Failed to load products. Try again later.</p>";
        });
}

function displayProducts() {
    const container = document.getElementById("product-list");
    if (!container) return;

    container.innerHTML = "";

    if (products.length === 0) {
        container.innerHTML = "<p>No products available 🛒</p>";
        return;
    }

    products.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
            <h3>${product.name}</h3>
            <p>${product.description}</p>
            <p><strong>₹${product.price}</strong></p>
            <button onclick="addToCart(${product.id})">Add to Cart</button>
        `;
        container.appendChild(card);
    });
}

// ---------------- CART LOGIC ----------------

function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;

    cart.push(product);
    localStorage.setItem("cart", JSON.stringify(cart));
    alert("✅ Product added to cart!");
}

function getCartItems() {
    return JSON.parse(localStorage.getItem("cart")) || [];
}

function clearCart() {
    cart = [];
    localStorage.removeItem("cart");
}

// ---------------- ORDER LOGIC ----------------

function placeOrder() {
    const email = localStorage.getItem("email");
    if (!email) {
        alert("⚠️ Please login first!");
        return;
    }

    const cartItems = getCartItems();
    if (cartItems.length === 0) {
        alert("🛒 Your cart is empty!");
        return;
    }

    const orderData = { email, products: cartItems };

    fetch("http://localhost:8080/api/order/place", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(orderData)
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                alert("🎉 Order placed successfully!");
                clearCart();
            } else {
                alert("❌ Failed to place order: " + data.message);
            }
        })
        .catch(err => {
            console.error("🚨 Order error:", err);
            alert("❌ Could not place order.");
        });
}

function loadOrders() {
    const email = localStorage.getItem("email");
    if (!email) {
        alert("⚠️ Please login to view orders");
        return;
    }

    fetch(`http://localhost:8080/api/order/${email}`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("orderContainer");
            if (!container) return;

            container.innerHTML = "";

            if (data.length === 0) {
                container.innerHTML = "<p>No orders found 🧾</p>";
                return;
            }

            data.forEach(order => {
                const orderDiv = document.createElement("div");
                orderDiv.className = "order-card";
                orderDiv.innerHTML = `
                    <h3>🆔 Order ID: ${order.id}</h3>
                    <p>📧 Email: ${order.email}</p>
                    <h4>🛍️ Products:</h4>
                    <ul>
                        ${order.products.map(p => `<li>${p.name} - ₹${p.price}</li>`).join("")}
                    </ul>
                `;
                container.appendChild(orderDiv);
            });
        })
        .catch(err => {
            console.error("🚨 Load orders error:", err);
        });
}

// ---------------- USER AUTH ----------------

function register() {
    const email = document.getElementById("registerEmail")?.value;
    const password = document.getElementById("registerPassword")?.value;

    if (!email || !password) {
        alert("⚠️ Enter email and password!");
        return;
    }

    fetch("http://localhost:8080/api/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                alert("✅ Registered successfully!");
                localStorage.setItem("email", email);
                window.location.href = "index.html";
            } else {
                alert("❌ " + data.message);
            }
        })
        .catch(err => {
            console.error("🚨 Registration error:", err);
            alert("❌ Registration failed");
        });
}
function login() {
    const email = document.getElementById("loginEmail")?.value;
    const password = document.getElementById("loginPassword")?.value;

    if (!email || !password) {
        alert("⚠️ Enter login credentials!");
        return;
    }

    fetch("http://localhost:8080/api/user/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                alert("✅ Login successful!");

                // Save the full user object for future use
                localStorage.setItem("loggedInUser", JSON.stringify(data.user));

                window.location.href = "index.html";
            } else {
                alert("❌ " + data.message);
            }
        })
        .catch(err => {
            console.error("🚨 Login error:", err);
            alert("❌ Login failed");
        });

}
