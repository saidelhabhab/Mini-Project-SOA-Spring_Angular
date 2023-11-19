import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CustomersComponent } from './customers.component';
import { CartComponent } from './components/cart/cart.component';
import { MyFactureComponent } from './components/my-facture/my-facture.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';


const routes: Routes = [
  { path: '', component: CustomersComponent },
  { path: 'dashboard', component: DashboardComponent},
  { path: 'cart', component: CartComponent},
  { path: 'my_orders', component: MyFactureComponent},
  { path: 'profile', component: UserProfileComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CostumersRoutingModule { }
