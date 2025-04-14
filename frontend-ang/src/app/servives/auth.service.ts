import { Injectable } from '@angular/core';

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

  constructor() { }

  public login(username : string, password : string) {
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


}
