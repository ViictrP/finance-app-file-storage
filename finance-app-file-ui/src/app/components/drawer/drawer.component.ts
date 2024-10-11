import { Component, OnInit } from '@angular/core';
import { routes } from '../../app.routes';
import { Router, RouterLink, RouterLinkActive, Routes } from '@angular/router';

@Component({
    selector: 'app-drawer',
    standalone: true,
    templateUrl: './drawer.component.html',
    imports: [
        RouterLink,
        RouterLinkActive,
    ],
})
export class DrawerComponent implements OnInit {
    private _menus: Routes = [];

    constructor(private readonly router: Router) {
    }

    ngOnInit(): void {
        this._menus = routes;
    }

    get menus(): Routes {
        return this._menus[1].children ?? [];
    }
}
