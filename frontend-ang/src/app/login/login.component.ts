import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AuthService} from '../servives/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  //initialisation d'une variable de type FormGroup (groupe de formulaire)
  public loginForm! : FormGroup;

  // Injection du service FormBuilder pour la gestion des formulaires
  constructor(private fb : FormBuilder
              ,private authService : AuthService
              ,private router : Router) {
  }

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    // Création du formulaire avec deux champs : username et password
    this.loginForm = this.fb.group({
      // Champs de formulaire avec des contrôles (gérer l'état, la valeur et la validation de ce champ. )
      username : this.fb.control(''),
      password : this.fb.control('')
    })
  }

  login() {
    let username = this.loginForm.value.username;
    let password = this.loginForm.value.password;

    let auth:boolean = this.authService.login(username, password);

    if (auth == true){
      this.router.navigateByUrl("/admin");
    }
  }
}
