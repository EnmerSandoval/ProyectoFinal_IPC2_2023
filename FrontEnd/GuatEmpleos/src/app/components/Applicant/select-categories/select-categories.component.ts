
import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AdministratorService } from 'src/app/services/administrator.service';
import { Category } from 'src/app/model/Category';
import { ApplicantService } from 'src/app/services/applicant.service';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-select-categories',
  templateUrl: './select-categories.component.html',
  styleUrls: ['./select-categories.component.css']
})
export class SelectCategoriesComponent {
  categoryList: Category[] = [];
  newCategoriesList: Category[] = [];
  category!: Category;
  user! : User;
  

  constructor(private userService: UserService, private administratorService: AdministratorService, private applicanteService: ApplicantService) {
    this.category = new Category();
    this.user = new User();
  }

  ngOnInit() {
    this.administratorService.getAllCategories().subscribe({
      next: (categories: Category[]) => {
        this.categoryList = categories;
      }
    });
  }

  addNewCategory(category: Category) {
    this.newCategoriesList.push({ ...category });
    this.disableCategory(category);
    this.category = new Category();
  }

  disableCategory(category: Category) {
    const index = this.categoryList.findIndex(c => c.numberCategory === category.numberCategory);
    if (index !== -1) {
      this.categoryList[index].disabled = true;
    }
  }

  deleteNewCategory(category: Category) {
    const index = this.newCategoriesList.findIndex(c => c.numberCategory === category.numberCategory);
    if (index !== -1) {
      this.newCategoriesList.splice(index, 1);
      this.enableCategory(category);
    }
  }

  enableCategory(category: Category) {
    const index = this.categoryList.findIndex(c => c.numberCategory === category.numberCategory);
    if (index !== -1) {
      this.categoryList[index].disabled = false;
    }
  }

  insertOwnCategory() {
    this.cuiUser();
    if(this.newCategoriesList.length === 0){
      alert("Debe seleccionar al menos una categoria");
      return;
    } else {
      this.applicanteService.insertOwnCategory(this.newCategoriesList, this.user.cui).subscribe({
        next: () => {
          alert("Categorias agregadas correctamente");
        },
        error: (err) => {
          alert(err.error.message);
        }
      });
    }
  }

  cuiUser() {
    this.user = this.getCurrentUserFromLocalStorage() ?? {} as User;
  }

  getCurrentUserFromLocalStorage(): User | null {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  }

}