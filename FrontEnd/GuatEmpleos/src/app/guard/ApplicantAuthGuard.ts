import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from 'src/app/services/AuthService';

@Injectable()
export class ApplicantAuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const user = this.authService.getCurrentUserFromLocalStorage();
    if (user && user.typeUser == 3) {
      console.log("ApplicantAuthGuard TRUE");
      return true; 
    } else {
      console.log(user);
      this.router.navigate(['/unauthorized']); 
      return false;
    }
  }
}
