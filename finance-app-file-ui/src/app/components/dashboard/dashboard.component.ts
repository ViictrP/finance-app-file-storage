import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        DatePipe,
    ],
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
    today = new Date();
}
