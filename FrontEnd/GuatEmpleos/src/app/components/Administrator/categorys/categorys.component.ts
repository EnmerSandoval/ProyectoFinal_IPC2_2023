import { Component } from '@angular/core';
import { Category } from 'src/app/model/Category';
import { AdministratorService } from 'src/app/services/administrator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-categorys',
  templateUrl: './categorys.component.html',
  styleUrls: ['./categorys.component.css']
})
export class CategorysComponent {
  categoryList: Category[] = [];

  constructor(private userService : UserService, private administratorService : AdministratorService) { }

  ngOnInit(){
    this.administratorService.getAllCategories().subscribe({
      next: (categories: Category[]) => {
        this.categoryList = categories;
      }
    });
  }

  onClickLogout(){
    this.userService.logOut();
  }

}
