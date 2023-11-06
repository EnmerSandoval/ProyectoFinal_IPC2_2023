import { Component } from '@angular/core';;
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent {
  user! : User;
  error : boolean = false;
  register : boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  } 

  onSubmit(){    
   this.userService.registerUser(this.user).subscribe(user => {
        console.log(this.user.typeUser);
        localStorage.setItem('user', JSON.stringify(this.user));
        this.register = true;
        this.redirect();
    }, error => {
      console.log(this.error);
      this.error = true;
    });
  }

  redirect(){
    if (this.user.typeUser == 2) {
      this.router.navigate(['/employer/description']);
    } else if(this.user.typeUser == 3){
      //applicant
    }
  }
}
