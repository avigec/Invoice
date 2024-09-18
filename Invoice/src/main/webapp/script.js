document.getElementById('add-item').addEventListener('click', function () {
    const invoiceBody = document.getElementById('invoice-body');
    const newRow = document.createElement('tr');
    
    newRow.innerHTML = `
        <td><input type="text" name="item-desc[]" class="item-desc" placeholder="Item Description"></td>
        <td><input type="number" name="item-qty[]" class="item-qty" placeholder="0"></td>
        <td><input type="number" name="item-price[]" class="item-price" placeholder="0.00"></td>
        <td><input type="number" name="item-tax[]" class="item-tax" placeholder="0.00"></td>
        <td class="item-total">Rs 0.00</td>
    `;
    
    invoiceBody.appendChild(newRow);
    calculateTotals();
});

function calculateTotals() {
    const rows = document.querySelectorAll('#invoice-body tr');
    let subtotal = 0;
    
    rows.forEach(row => {
        const qty = parseFloat(row.querySelector('.item-qty').value) || 0;
        const price = parseFloat(row.querySelector('.item-price').value) || 0;
        const tax = parseFloat(row.querySelector('.item-tax').value) || 0;
        const total = qty * price * (1 + tax / 100);
        
        row.querySelector('.item-total').textContent = `Rs ${total.toFixed(2)}`;
        subtotal += total;
    });
    
    document.getElementById('subtotal').textContent = `Rs ${subtotal.toFixed(2)}`;
    document.getElementById('total').textContent = `Rs ${subtotal.toFixed(2)}`;
}



