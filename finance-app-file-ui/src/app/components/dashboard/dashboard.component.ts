import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import User from '../../model/user';

@Component({
    selector: 'app-dashboard',
    imports: [
        DatePipe,
    ],
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
    today = new Date();
    user: User = {
        name: 'Victor'
    };
}
