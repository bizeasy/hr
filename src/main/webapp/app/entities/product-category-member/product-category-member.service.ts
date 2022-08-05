import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductCategoryMember } from 'app/shared/model/product-category-member.model';

type EntityResponseType = HttpResponse<IProductCategoryMember>;
type EntityArrayResponseType = HttpResponse<IProductCategoryMember[]>;

@Injectable({ providedIn: 'root' })
export class ProductCategoryMemberService {
  public resourceUrl = SERVER_API_URL + 'api/product-category-members';

  constructor(protected http: HttpClient) {}

  create(productCategoryMember: IProductCategoryMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productCategoryMember);
    return this.http
      .post<IProductCategoryMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productCategoryMember: IProductCategoryMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productCategoryMember);
    return this.http
      .put<IProductCategoryMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductCategoryMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductCategoryMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productCategoryMember: IProductCategoryMember): IProductCategoryMember {
    const copy: IProductCategoryMember = Object.assign({}, productCategoryMember, {
      fromDate:
        productCategoryMember.fromDate && productCategoryMember.fromDate.isValid() ? productCategoryMember.fromDate.toJSON() : undefined,
      thruDate:
        productCategoryMember.thruDate && productCategoryMember.thruDate.isValid() ? productCategoryMember.thruDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productCategoryMember: IProductCategoryMember) => {
        productCategoryMember.fromDate = productCategoryMember.fromDate ? moment(productCategoryMember.fromDate) : undefined;
        productCategoryMember.thruDate = productCategoryMember.thruDate ? moment(productCategoryMember.thruDate) : undefined;
      });
    }
    return res;
  }
}
