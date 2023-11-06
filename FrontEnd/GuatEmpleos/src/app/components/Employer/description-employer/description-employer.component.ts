import { Component } from '@angular/core';
import { Employer } from 'src/app/model/Employer';
import { EmployerService } from 'src/app/services/employer.service';
import { User } from 'src/app/model/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-description-employer',
  templateUrl: './description-employer.component.html',
  styleUrls: ['./description-employer.component.css']
})
export class DescriptionEmployerComponent {
  error: boolean = false;
  employer!: Employer;
  user!: User;

  constructor(private employerService: EmployerService, private router: Router) {
    this.employer = new Employer();
  }

  ngOnInit() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
    }
  }

  onSubmit() {
    this.employer.cui = this.user.cui;
    this.employerService.descriptionEmployer(this.employer).subscribe(employer => {
      console.log("Se ha registrado");
      this.error = false;
      this.router.navigate(['/employer/regiterCard']);
    }, error => {
      console.log(this.error);
      this.error = true;
    });
  }

  
  
}
