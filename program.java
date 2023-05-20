import java.util.Scanner;

class Cart{
    int priceA,priceB,priceC;
    int numA,numB,numC;
    int totalWrap, cartTotal, totalQuantity, shippingFee;
    String appliedDiscount;
    double discount;
    Scanner s;

    Cart(){}

    Cart(int priceA,int priceB,int priceC){
        this.priceA = priceA;
        this.priceB = priceB;
        this.priceC = priceC;
        totalWrap = 0;
        appliedDiscount = "Not Applicable for Discount";
        s = new Scanner(System.in);
        System.out.println("Product Name\tPrice");
        System.out.println("A \t \t"+priceA+"$");
        System.out.println("B \t \t"+priceB+"$");
        System.out.println("C \t \t"+priceC+"$");
        System.out.println();
    }

    int validate(int n){    //Validate the number of quantity of each product to be wrapped

        int w = s.nextInt();
        while(w<0 || w>n){
            System.out.println("Enter a valid Quantity to be wrapped!!");
            w = s.nextInt();
        }
        return w;
    }

    int validate(){         //Validate the number of quantity of items entered

        int n = s.nextInt();
        while(n<0){
            System.out.println("Enter a valid Quantity!! \nQuantity cannot be negative");
            n = s.nextInt();
        }
        return n;
    }

    void takeInput(){
        System.out.print("Quantity of product A : ");
        numA = validate();
        System.out.print("Number of A to be gift wrapped : ");
        totalWrap += validate(numA);

        System.out.print("Quantity of product B : ");
        numB = validate();
        System.out.print("Number of B to be gift wrapped : ");
        totalWrap += validate(numB);

        System.out.print("Quantity of product C : ");
        numC = validate();
        System.out.print("Number of C to be gift wrapped : ");
        totalWrap += validate(numC);

        totalQuantity = numA + numB + numC;
        cartTotal = numA*priceA + numB*priceB + numC*priceC;
        shippingFee = totalQuantity/10;
        if(totalQuantity % 10 != 0)
            shippingFee++;
        shippingFee *= 5;
    }

    void flat_10_discount(){
        if(cartTotal > 200){
            discount = Math.max(discount,10.0);
            if(discount==10.0)
                appliedDiscount = "flat_10_discount";
        }
    }

    void bulk_5_discount(){
        if(numA<=10 && numB<=10 && numC<=10)
            return;
        
        double n = 0;
        if(numC > 10)
            n += 0.05*numC*priceC;
        if(numB > 10)
            n += 0.05*numB*priceB;
        if(numA > 10)
            n += 0.05*numA*priceA; 
        discount = Math.max(discount, n);
        if(discount == n)
            appliedDiscount = "bulk_5_discount";
    }
    
    void bulk_10_discount(){
        double n = 0;
        if(totalQuantity>20){
            n = cartTotal*0.1;
            discount = Math.max(discount,n);
            if(discount == n)
                appliedDiscount = "bulk_10_discount";
        }
    }

    void tiered_50_discount(){
        double n = 0;
        int f = 0;
        if(totalQuantity > 30 && (numA>15 || numB>15 || numC>15)){
            f = 1;
            if(numC > 15)
                n += (numC-15) * priceC * 0.5;
            if (numB > 15)
                n += (numB-15) * priceB * 0.5;
            if(numA > 15)
                n += (numA-15) * priceA * 0.5;
        }
        discount = Math.max(n, discount);
        if(n==discount && f==1)
            appliedDiscount = "tiered_50_discount";
    }

    void applyDiscount(){
        flat_10_discount();
        bulk_5_discount();
        bulk_10_discount();
        tiered_50_discount();
    }
    

    void displayTable(){
        System.out.println("---------------------------------------------------------");
        System.out.println("\nProduct_name\tPrice\tQuantity\tTotal_amount");
        System.out.println("Product A\t"+priceA+"$\t"+numA+" \t\t"+ priceA*numA +"$");
        System.out.println("Product B\t"+priceB+"$\t"+numB+" \t\t"+ priceB*numB +"$");
        System.out.println("Product C\t"+priceC+"$\t"+numC+" \t\t"+ priceC*numC +"$\n");

        System.out.println("Cart Subtotal \t: "+cartTotal + "$");
        System.out.println("Discount Name \t: "+appliedDiscount);
        System.out.format("Discount Amount : %.2f$\n",discount);
        System.out.println("Shipping Fee \t: "+shippingFee +"$");
        System.out.println("Wrapping Fee \t: "+ totalWrap +"$\n");

        System.out.format("Total amount \t: %.2f$\n\n",(shippingFee+totalWrap+cartTotal-discount));
    }
}

public class Task{

    public static void main(String[] args) {
        int priceA = 20;
        int priceB = 40;
        int priceC = 50;

        Cart c = new Cart(priceA,priceB,priceC);
        c.takeInput();
        c.applyDiscount();
        c.displayTable();
    }
}
