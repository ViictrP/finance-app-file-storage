import { Component, OnInit } from '@angular/core';
import { routes } from '../../app.routes';
import Menu from '../../types/menu.type';
import { Router } from '@angular/router';

@Component({
    selector: 'app-drawer',
    standalone: true,
    templateUrl: './drawer.component.html',
    imports: [
    ],
})
export class DrawerComponent implements OnInit {
    menus: Menu[] = [];

    constructor(private readonly router: Router) {
    }

    ngOnInit(): void {
        this.menus = routes;
    }

    async navigateTo(label: string) {
        return this.router.navigate([`/dashboard/${label}`]);
    }
}
