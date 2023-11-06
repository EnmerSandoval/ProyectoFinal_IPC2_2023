import { Component } from '@angular/core';
import { Employer } from 'src/app/model/Employer';
import { EmployerService } from 'src/app/services/employer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-description-employer',
  templateUrl: './description-employer.component.html',
  styleUrls: ['./description-employer.component.css']
})
export class DescriptionEmployerComponent {
  error : boolean = false;
  employer! : Employer;

  constructor(private employerService: EmployerService, private router: Router) {
    this.employer = new Employer();
  }

  onSubmit(){
    this.employerService.descriptionEmployer(this.employer).subscribe(employer =>{
      if(employer) {
        this.redirect();
      }
    }, error => {
      console.log(this.error);
      this.error = true;
    });
  }

  redirect(){
    //this.router.navigate(['/employer/registerCard']);
  }
}
