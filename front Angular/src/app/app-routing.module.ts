import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { DashboardComponent } from './admin/components/dashboard/dashboard.component';


const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"",component: LoginComponent},
  {path:"signup",component:SignupComponent},
  { path: 'customer', loadChildren: () => import('./customers/customers.module').then(m => m.CustomersModule) }, 
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
