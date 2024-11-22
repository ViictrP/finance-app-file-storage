import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    imports: [
        RouterOutlet,
    ]
})
export class HomeComponent {}
