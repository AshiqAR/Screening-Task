def getQuantity(productName):               #To validate that quantity entered is not negative 

    q = int(input(f"Enter the quantity of {productName} : "))
    if q<0:
        print('Enter a valid product quantity !!\nQuantity cannot be negative')
        return getQuantity(productName)
    return q


def setDiscount(cart, cartTotal, totalQuantity):        #assigns the discount type
    discount = 0
    appliedDiscount = 'Not Applicable for Discount'
    if cartTotal>200:
        discount = 10
        appliedDiscount = 'flat_10_discount'
    
    n,f = 0,0
    for i in range(3):
        if cart[i][2]>10:
            f = 1
            n += cart[i][1]*cart[i][2]*0.05
    discount = max(n,discount)
    if(f==1):
        appliedDiscount = 'bulk_5_discount'
    
    if totalQuantity>20:
        n = cartTotal*.1
        discount = max(discount,n)
        appliedDiscount = 'bulk_10_discount'

    n,f = 0,0
    if totalQuantity > 30:
        for i in range(3):
            if cart[i][2]>15:
                f = 1
                n += (cart[i][2]-15)*cart[i][1]*0.5
        discount = max(discount,n)
        if discount == n and f == 1:
            appliedDiscount = 'tiered_50_discount'

    return appliedDiscount, discount




products = [('Product A',20),('Product B',40),('Product C',50)]
cart = []
cartTotal = totalQuantity = totalWrap = 0


print('Product_name \t Price')
for product,price in products:
    print(product,'\t',price,'$')
print()

for product,price in products:
    q = getQuantity(product)
    is_wrapped = False
    if q != 0:
        is_wrapped = input(f'is {product} wrapped as a gift? (y/n) : ') == 'y'

    cart.append((product,price,q,is_wrapped))
    cartTotal += q*price
    totalQuantity += q
    if is_wrapped:
        totalWrap += q
    


print('-------------------------------------------------------')
print('Product_name \t Price \t Quantity \t Total_price')
for product,price,q,is_wrapped in cart:
    print(f'{product} \t {price} $\t {q} \t\t {q*price} $')

shippingFee = 5*(totalQuantity//10) if totalQuantity%10 == 0 else 5*((totalQuantity//10)+1)
appliedDiscount, discount = setDiscount(cart, cartTotal, totalQuantity)

print(f'\nCart Subtotal \t: {cartTotal} $')
print(f'Discount Name \t: {appliedDiscount}')
print(f'Discount Amount : {discount} $')
print(f'Shipping Fee \t: {shippingFee} $')
print(f'Wrapping Fee \t: {totalWrap} $')

print(f'\nTotal Amount \t: {cartTotal+shippingFee+totalWrap-discount} $')
