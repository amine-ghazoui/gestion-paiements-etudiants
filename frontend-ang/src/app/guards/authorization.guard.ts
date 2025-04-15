import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from '@angular/core';
import {AuthService} from '../servives/auth.service';

@Injectable()
export class AuthorizationGuard implements CanActivate{

  constructor(private authService : AuthService, private router : Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {

    if (this.authService.isAuthenticated){
      // les roles qui sont exigés pour accéder à la route
      let requiredRoles = route.data['roles'];
      // les roles de l'utilisateur authentifié
      let userRoles = this.authService.roles;

      // Vérification si l'utilisateur a au moins un des rôles requis
      for(let role of userRoles){
        if (requiredRoles.includes(role)){
          return true;
        }
      }
      return true;
    }
    else {
      this.router.navigateByUrl('/login');
      return false
    }
  }

}
