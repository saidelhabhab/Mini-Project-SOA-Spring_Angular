import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  users :any;
  constructor(private adminService:AdminService,private snackBar:MatSnackBar,) {}


  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(){
    this.adminService.getAllUsers().subscribe(res=>{
      console.log("getAllUsers", res);
      this.users = res;
    })
  }

  

  deleteClient(clientId:any){

    this.adminService.deleteClient(clientId).subscribe(res=>{

      this.snackBar.open("Deleted Successfully", "Close", { duration: 2000 });
       this.getUsers();
    })



  }



  
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
      });

  }

}
