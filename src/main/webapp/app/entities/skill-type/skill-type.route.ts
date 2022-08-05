import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISkillType, SkillType } from 'app/shared/model/skill-type.model';
import { SkillTypeService } from './skill-type.service';
import { SkillTypeComponent } from './skill-type.component';
import { SkillTypeDetailComponent } from './skill-type-detail.component';
import { SkillTypeUpdateComponent } from './skill-type-update.component';

@Injectable({ providedIn: 'root' })
export class SkillTypeResolve implements Resolve<ISkillType> {
  constructor(private service: SkillTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISkillType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((skillType: HttpResponse<SkillType>) => {
          if (skillType.body) {
            return of(skillType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SkillType());
  }
}

export const skillTypeRoute: Routes = [
  {
    path: '',
    component: SkillTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.skillType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SkillTypeDetailComponent,
    resolve: {
      skillType: SkillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.skillType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SkillTypeUpdateComponent,
    resolve: {
      skillType: SkillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.skillType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SkillTypeUpdateComponent,
    resolve: {
      skillType: SkillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.skillType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
