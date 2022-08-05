import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductStoreFacility } from 'app/shared/model/product-store-facility.model';

type EntityResponseType = HttpResponse<IProductStoreFacility>;
type EntityArrayResponseType = HttpResponse<IProductStoreFacility[]>;

@Injectable({ providedIn: 'root' })
export class ProductStoreFacilityService {
  public resourceUrl = SERVER_API_URL + 'api/product-store-facilities';

  constructor(protected http: HttpClient) {}

  create(productStoreFacility: IProductStoreFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productStoreFacility);
    return this.http
      .post<IProductStoreFacility>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productStoreFacility: IProductStoreFacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productStoreFacility);
    return this.http
      .put<IProductStoreFacility>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductStoreFacility>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductStoreFacility[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productStoreFacility: IProductStoreFacility): IProductStoreFacility {
    const copy: IProductStoreFacility = Object.assign({}, productStoreFacility, {
      fromDate:
        productStoreFacility.fromDate && productStoreFacility.fromDate.isValid() ? productStoreFacility.fromDate.toJSON() : undefined,
      thruDate:
        productStoreFacility.thruDate && productStoreFacility.thruDate.isValid() ? productStoreFacility.thruDate.toJSON() : undefined,
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
      res.body.forEach((productStoreFacility: IProductStoreFacility) => {
        productStoreFacility.fromDate = productStoreFacility.fromDate ? moment(productStoreFacility.fromDate) : undefined;
        productStoreFacility.thruDate = productStoreFacility.thruDate ? moment(productStoreFacility.thruDate) : undefined;
      });
    }
    return res;
  }
}
