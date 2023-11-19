import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Route, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.scss']
})
export class UpdateProductComponent implements OnInit {
  
  productForm:FormGroup;
  listOfCategories:any =[];
  selectedFile :File | null;
  imagePreview:string| ArrayBuffer | null;
  dialogRef: any;

  productId: number;
  updatedProduct: any = {}; 

  constructor(
    private fb : FormBuilder,
    private snackBar:MatSnackBar,
    private adminService:AdminService,
    private router:Router,
    private route:ActivatedRoute
    ){}

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () =>{
      this.imagePreview = reader.result;
      
    }
    reader.readAsDataURL(this.selectedFile);
  }


  ngOnInit(): void {
    this.productForm = this.fb.group({

      categoryId:[null,Validators.required],
      name: [null,Validators.required],
      price:[null,Validators.required],
      description:[null,Validators.required]
      
      
    });

    this.getAllCategories();


   
  
    this.route.params.subscribe(params => {
      this.productId = +params['productId']; 
      console.log("productId : " +this.productId)
    });

     // Fetch existing product details
      this.adminService.getProductsById(this.productId).subscribe(
        (product) => {
          console.log("this.updatedProduct : " +this.updatedProduct)
          this.updatedProduct = product;
        },
        (error) => {
          console.error('Error fetching product details', error);
        }
      );
  
  }

  getAllCategories(){
   this.adminService.getAllCategories().subscribe((res)=>{
      
       console.log("categories", res)
        this.listOfCategories=res;
    })
  }

  updateProduct():void{
    
     if(this.productForm.valid){

    
      let data={...this.productForm.value}
       console.log("data",data)

      const formData : FormData = new FormData()
      formData.append('img',this.selectedFile);
      formData.append('categoryId',this.productForm.get('categoryId').value);
      formData.append('description',this.productForm.get('description').value);
      formData.append('name',this.productForm.get('name').value);
      formData.append('price',this.productForm.get('price').value);
     
      this.adminService.updateProduct(formData,this.productId).subscribe((res) =>{
        
        console.log("results : " +res)
        alert("updated Successfully");

        if (res.id !=null) {
          
          this.router.navigateByUrl('/admin/dashboard');
          this.snackBar.open('Product updated successfully','OK',{duration:3000})
          this.dialogRef.close();
        }else{
            this.snackBar.open('Failed to update product','OK',{duration:3000})
        }
      },err=>console.error(err));
            
    }else{
      for(const i in this.productForm.controls){
        this.productForm.controls[i].markAsDirty();
        this.productForm.controls[i].updateValueAndValidity();
      }
      alert("not Successfully");    

    }

    
    
  }


}
