import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemIssuance } from 'app/shared/model/item-issuance.model';

type EntityResponseType = HttpResponse<IItemIssuance>;
type EntityArrayResponseType = HttpResponse<IItemIssuance[]>;

@Injectable({ providedIn: 'root' })
export class ItemIssuanceService {
  public resourceUrl = SERVER_API_URL + 'api/item-issuances';

  constructor(protected http: HttpClient) {}

  create(itemIssuance: IItemIssuance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemIssuance);
    return this.http
      .post<IItemIssuance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemIssuance: IItemIssuance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemIssuance);
    return this.http
      .put<IItemIssuance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemIssuance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemIssuance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(itemIssuance: IItemIssuance): IItemIssuance {
    const copy: IItemIssuance = Object.assign({}, itemIssuance, {
      issuedDate: itemIssuance.issuedDate && itemIssuance.issuedDate.isValid() ? itemIssuance.issuedDate.toJSON() : undefined,
      fromDate: itemIssuance.fromDate && itemIssuance.fromDate.isValid() ? itemIssuance.fromDate.toJSON() : undefined,
      thruDate: itemIssuance.thruDate && itemIssuance.thruDate.isValid() ? itemIssuance.thruDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.issuedDate = res.body.issuedDate ? moment(res.body.issuedDate) : undefined;
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((itemIssuance: IItemIssuance) => {
        itemIssuance.issuedDate = itemIssuance.issuedDate ? moment(itemIssuance.issuedDate) : undefined;
        itemIssuance.fromDate = itemIssuance.fromDate ? moment(itemIssuance.fromDate) : undefined;
        itemIssuance.thruDate = itemIssuance.thruDate ? moment(itemIssuance.thruDate) : undefined;
      });
    }
    return res;
  }
}
