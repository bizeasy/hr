import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKeywordType, KeywordType } from 'app/shared/model/keyword-type.model';
import { KeywordTypeService } from './keyword-type.service';
import { KeywordTypeComponent } from './keyword-type.component';
import { KeywordTypeDetailComponent } from './keyword-type-detail.component';
import { KeywordTypeUpdateComponent } from './keyword-type-update.component';

@Injectable({ providedIn: 'root' })
export class KeywordTypeResolve implements Resolve<IKeywordType> {
  constructor(private service: KeywordTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKeywordType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((keywordType: HttpResponse<KeywordType>) => {
          if (keywordType.body) {
            return of(keywordType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KeywordType());
  }
}

export const keywordTypeRoute: Routes = [
  {
    path: '',
    component: KeywordTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.keywordType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KeywordTypeDetailComponent,
    resolve: {
      keywordType: KeywordTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.keywordType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KeywordTypeUpdateComponent,
    resolve: {
      keywordType: KeywordTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.keywordType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KeywordTypeUpdateComponent,
    resolve: {
      keywordType: KeywordTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.keywordType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
