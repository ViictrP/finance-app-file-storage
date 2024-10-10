import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import Menu from './types/menu.type';

//TODO fix navigation
export const routes: Routes & Menu[] = [
    {
        path: '',
        component: DashboardComponent,
        label: 'Dashboard',
        icon: 'world'
    }
];
