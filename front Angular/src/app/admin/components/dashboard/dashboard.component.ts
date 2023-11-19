import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  products :any[] = [];
  searchProductForm:FormGroup;

  constructor(
    private adminService:AdminService,
    private fb:FormBuilder,
    private snackBar:MatSnackBar,
    ) { }


  ngOnInit(): void {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title:[null,Validators.required]
    })
  }

  getAllProducts(){
    this.products = [];

    this.adminService.getAllProducts().subscribe(res=>{
      res.forEach(element=>{
        element.processedImg ='data:image/jpeg;base64,'+element.byteImg;
        this.products.push(element);
      })
    })
  }

  submitForm (){
    this.products = [];
    const title = this.searchProductForm.get('title')!.value;
    this.adminService.getAllProductsByName(title).subscribe(res=>{
      res.forEach(element=>{
        element.processedImg ='data:image/jpeg;base64,'+element.byteImg;
        this.products.push(element);
      })
      //console.log("product"+ this.products);
    })
  }

  deleteProduct(productId:any){


        this.adminService.deleteProduct(productId).subscribe(res=>{
          if(res.body){
            let index = this.products.findIndex((obj => obj._id === productId));
            this.products.splice(index,1)
            this.snackBar.open("Deleted Successfully", "Close", { duration: 2000 });
            this.getAllProducts();

            }else{
              this.snackBar.open("Error Occurred! Try Again Later.", "Close", { duration: 3000 });
              }

        },err=>{this.snackBar.open("Error Occurred! Try Again Later...", "Close", 
        { duration: 3000 });})
             
      }
    



  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
      });

  }

}
