import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {
  
  clientForm:FormGroup;
  dialogRef: any;

  clientId: number;
  updatedClient: any = {}; 

  constructor(
    private fb : FormBuilder,
    private snackBar:MatSnackBar,
    private adminService:AdminService,
    private router:Router,
    private route:ActivatedRoute
    ){}

  

    ngOnInit(): void {
      this.clientForm = this.fb.group({
        firstName: [null,[Validators.required]],
        lastName: [null,[Validators.required]],
        phone: [null,[Validators.required]],
        address: [null,[Validators.required]],
        city: [null,[Validators.required]],      
      })

      this.route.params.subscribe(params => {
        this.clientId = +params['clientId']; 
        console.log("clientId : " +this.clientId)
      });

      
     // Fetch existing product details
     this.adminService.getClientById(this.clientId).subscribe(
      (product) => {
        console.log("this.updatedProduct : " +this.updatedClient)
        this.updatedClient = product;
      },
      (error) => {
        console.error('Error fetching product details', error);
      }
    );

    }


  updateClient():void{
    

    if(this.clientForm.valid) {

    const user={...this.clientForm.value}

    console.log('user',user);
    

    this.adminService.updateClient(user,this.clientId).subscribe((res)=>{
      console.log("Updated Successfully");
      this.openSnackBar("Updated successful","Close")
      
      setTimeout(()=>{
        this.router.navigateByUrl('admin/users')
        },2000)
        
      },err =>{
          console.error(err);
          this.openSnackBar("Error in Updated", "Close")
          });
    }
  }


openSnackBar(message: string, action: string) {
  this.snackBar.open(message, action, {
    duration: 3000,
    });

}

}
