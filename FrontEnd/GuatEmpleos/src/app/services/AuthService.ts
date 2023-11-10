import { Injectable } from '@angular/core';
import { User } from 'src/app/model/User';

@Injectable()
export class AuthService {
  private currentUser: User | null = null;

  constructor() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.currentUser = JSON.parse(storedUser);
    }
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  getCurrentUserFromLocalStorage(): User | null {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  }
}
