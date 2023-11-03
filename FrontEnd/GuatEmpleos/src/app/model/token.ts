import { DatePipe } from "@angular/common";

export class Token {
    token!: string;
    cuiUser! : number;
    state : boolean = false;
    tokenDate! : Date;
    tokenExpirationDate!: Date;
}
