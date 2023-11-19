import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { PlaceOrderComponent } from '../place-order/place-order.component';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent  implements OnInit {
  
  facture :any;
  //facture:any;
  couponForm! : FormGroup;

  constructor(
    private customerService:CustomerService,
    private fb:FormBuilder,
    private snackBar:MatSnackBar,
    public dialog: MatDialog,
    private router:Router
    ) { }


  ngOnInit() {
    this.couponForm = this.fb.group({
      code: [null,Validators.required]
    });

    this.getCart();

    this.facture = this.customerService.getCartByClientId();
  }

  applyCoupon(){

    //let data = {...this.couponForm.get(['code'])!.value}
    this.customerService.applyCoupon(this.couponForm.get(['code'])!.value).subscribe(res=>{
     // console.log("data" +res)
      this.openSnackBar('Coupon applied Successfully','OK');
      this.getCart();
    },
    err => {

      if (err.error){
        this.openSnackBar(err.error,'OK')
        }else{
          this.openSnackBar("Something went wrong","OK")
          }
    }
    )
  }

  getCart(){
    this.facture = []
    this.customerService.getCartByClientId().subscribe( res => {

      this.facture = res ;
    }
    );
   }
       
   deleteFacture(productId:any){

    this.customerService.delete(productId).subscribe(res=>{

    });
// console.log("data" +res)

    this.openSnackBar('Facture is deleted ','OK');
    this.router.navigateByUrl('customer/dashboard');
        
  

   }
   

  

  increaseProductQuantity(productId:any){

    this.customerService.increaseProductQuantity(productId).subscribe(res=>{

      this.openSnackBar('Product Quantity Increase (+) ','OK');
      this.getCart();
    })

  }

  decreaseProductQuantity(productId:any){

    this.customerService.decreaseProductQuantity(productId).subscribe(res=>{

      this.openSnackBar('Product Quantity deduction (-) ','OK');
      this.getCart();
    })

  }

  placeOrder(){
    this.dialog.open(PlaceOrderComponent);
  }


  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
      });

  }
}
