import { Injectable } from '@angular/core';
import {Route, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public users : any = {
    admin : {password : '1234', roles : ['STUDENT', 'ADMIN']},
    user1 : {password : '1234', roles : ['STUDENT']},
  }

  // Variables publiques pour stocker les informations de l'utilisateur

  public username : any;
  public isAuthenticated : boolean = false;
  public roles : string[] = [];

  constructor(private router : Router) { }

  public login(username : string, password : string) : boolean {
    // Vérification des informations d'identification de l'utilisateur
    if(this.users[username] && this.users[username]['password'] == password){

      this.username = username;
      this.isAuthenticated = true;
      this.roles = this.users[username]['roles'];
      return true;
    }
    else {
      return false;
    }
  }


  logout() {
    this.isAuthenticated = false;
    this.roles = [];
    this.username = undefined;
    this.router.navigateByUrl('/login');
  }
}
