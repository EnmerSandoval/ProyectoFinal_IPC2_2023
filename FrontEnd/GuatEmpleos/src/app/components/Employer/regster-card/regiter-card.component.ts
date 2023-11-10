import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Card } from 'src/app/model/Card';
import { User } from 'src/app/model/User';
import { EmployerService } from 'src/app/services/employer.service';


@Component({
  selector: 'app-regiter-card',
  templateUrl: './regiter-card.component.html',
  styleUrls: ['./regiter-card.component.css']
})
export class RegiterCardComponent {
  error: boolean = false;
  registerCard: boolean = false;
  card: Card;
  user!: User;
  
  
  constructor(private employerService: EmployerService, private router : Router) { 
    this.card = new Card();
  }

  ngOnInit() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
    }
  }

  onSubmit() {
      this.card.cuiEmployer = this.user.cui;
      this.employerService.registerCardEmployer(this.card).subscribe( card => {
        this.registerCard = true;
      });
  }

  redirect(){
    this.router.navigate(['/main-house']);
  }
}
