let balance = 0.0;

// Function to load available products from the REST API
async function loadProducts() {
    try {
        const response = await fetch('/vending/products');
        const products = await response.json();
        const productTableBody = document.getElementById('product-table-body');

        productTableBody.innerHTML = '';  // Clear the previous product rows

        // Populate the product table with products from the API
        products.forEach(product => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${product.name}</td>
                <td>${product.price.toFixed(2)}</td>
                <td>${product.quantity}</td>
                <td><button onclick="selectProduct(${product.id})">Select</button></td>
            `;
            productTableBody.appendChild(row);
        });
    } catch (error) {
        displayMessage('Failed to load products', 'red');
    }
}

// Function to insert cash
function insertCash() {
    const cashInput = document.getElementById('cash-input');
    const amount = parseFloat(cashInput.value);

    if (amount > 0) {
        balance += amount;
        document.getElementById('balance-display').innerText = `$${balance.toFixed(2)}`;
        cashInput.value = '';  // Clear the input field
        displayMessage(`Added $${amount.toFixed(2)} to balance.`, 'green');
    } else {
        displayMessage('Please enter a valid amount.', 'red');
    }
}

// Function to select a product
async function selectProduct(productId) {
    try {
        const response = await fetch(`/vending/select?productId=${productId}`, { method: 'POST' });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message);
        }

        const result = await response.json();
        const productPrice = result.product.price;

        if (balance >= productPrice) {
            balance -= productPrice;
            document.getElementById('balance-display').innerText = `$${balance.toFixed(2)}`;
            displayMessage(`Product purchased: ${result.product.name}. Change returned: $${result.change.toFixed(2)}.`, 'green');
            loadProducts();  // Refresh the product list to reflect new quantities
        } else {
            displayMessage('Insufficient funds for this product.', 'red');
        }
    } catch (error) {
        displayMessage(error.message, 'red');
    }
}

// Function to display messages
function displayMessage(message, color) {
    const messageDisplay = document.getElementById('message-display');
    messageDisplay.innerText = message;
    messageDisplay.style.color = color;
}

// Load products when the page is loaded
window.onload = loadProducts;
