import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent  implements OnInit {
  
  clientForm:FormGroup;
  dialogRef: any;

  clientId: number;
  updatedClient: any = {}; 

  constructor(
    private fb : FormBuilder,
    private snackBar:MatSnackBar,
    private customerService:CustomerService,
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

      
      
     // Fetch existing product details
     this.customerService.getProfileById().subscribe(
      (profile) => {
        
        this.updatedClient = profile;
      },
      (error) => {
        console.error('Error fetching profile details', error);
      }
    );

    }


  updateClient():void{
    

    if(this.clientForm.valid) {

    const user={...this.clientForm.value}

    console.log('user',user);
    

    this.customerService.updateProfile(user).subscribe((res)=>{
      console.log("Updated Successfully");
      this.openSnackBar("Updated successful","Close")
      
      setTimeout(()=>{
        this.router.navigateByUrl('customer/profile')
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
