import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {LoginComponent} from './login/login.component';
import {LoadStudentsComponent} from './load-students/load-students.component';
import {LoadPaymentsComponent} from './load-payments/load-payments.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {StudentsComponent} from './students/students.component';
import {PaymentsComponent} from './payments/payments.component';
import {AdminTemplateComponent} from './admin-template/admin-template.component';
import {AuthGuard} from './guards/auth.guard';
import {AuthorizationGuard} from './guards/authorization.guard';

const routes: Routes = [
  {path : "", component : LoginComponent},
  {path : "login", component : LoginComponent},
  {path : "admin", component : AdminTemplateComponent,
    canActivate : [AuthGuard],
    children : [
      {path : "home", component : HomeComponent},
      {path : "profile", component : ProfileComponent},
      {
        path : "loadStudents", component : LoadStudentsComponent,
        canActivate : [AuthorizationGuard], data : {roles : ['ADMIN']}
      },
      {path : "loadPayments", component : LoadPaymentsComponent},
      {path : "dashboard", component : DashboardComponent},
      {path : "students", component : StudentsComponent},
      {path : "payments", component : PaymentsComponent},
    ]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

/*
data : {roles : ['ADMIN']} : cettez les rôles requis pour accéder à cette route.
canActivate : [AuthorizationGuard] : cette route est protégée par le AuthorizationGuard (c-est-à-dire que l'utilisateur doit être authentifié et avoir le rôle requis pour y accéder).
canActivate : [AuthGuard] : cette route est protégée par le AuthGuard (c-est-à-dire que l'utilisateur doit être authentifié pour y accéder).
 */
