import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEquipmentUsageLog, EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';
import { EquipmentUsageLogService } from './equipment-usage-log.service';
import { EquipmentUsageLogComponent } from './equipment-usage-log.component';
import { EquipmentUsageLogDetailComponent } from './equipment-usage-log-detail.component';
import { EquipmentUsageLogUpdateComponent } from './equipment-usage-log-update.component';

@Injectable({ providedIn: 'root' })
export class EquipmentUsageLogResolve implements Resolve<IEquipmentUsageLog> {
  constructor(private service: EquipmentUsageLogService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipmentUsageLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((equipmentUsageLog: HttpResponse<EquipmentUsageLog>) => {
          if (equipmentUsageLog.body) {
            return of(equipmentUsageLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EquipmentUsageLog());
  }
}

export const equipmentUsageLogRoute: Routes = [
  {
    path: '',
    component: EquipmentUsageLogComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.equipmentUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EquipmentUsageLogDetailComponent,
    resolve: {
      equipmentUsageLog: EquipmentUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.equipmentUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EquipmentUsageLogUpdateComponent,
    resolve: {
      equipmentUsageLog: EquipmentUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.equipmentUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EquipmentUsageLogUpdateComponent,
    resolve: {
      equipmentUsageLog: EquipmentUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.equipmentUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
