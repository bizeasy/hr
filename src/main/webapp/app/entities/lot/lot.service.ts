import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILot } from 'app/shared/model/lot.model';

type EntityResponseType = HttpResponse<ILot>;
type EntityArrayResponseType = HttpResponse<ILot[]>;

@Injectable({ providedIn: 'root' })
export class LotService {
  public resourceUrl = SERVER_API_URL + 'api/lots';

  constructor(protected http: HttpClient) {}

  create(lot: ILot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lot);
    return this.http
      .post<ILot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lot: ILot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lot);
    return this.http
      .put<ILot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lot: ILot): ILot {
    const copy: ILot = Object.assign({}, lot, {
      creationDate: lot.creationDate && lot.creationDate.isValid() ? lot.creationDate.toJSON() : undefined,
      expirationDate: lot.expirationDate && lot.expirationDate.isValid() ? lot.expirationDate.toJSON() : undefined,
      retestDate: lot.retestDate && lot.retestDate.isValid() ? lot.retestDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.expirationDate = res.body.expirationDate ? moment(res.body.expirationDate) : undefined;
      res.body.retestDate = res.body.retestDate ? moment(res.body.retestDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lot: ILot) => {
        lot.creationDate = lot.creationDate ? moment(lot.creationDate) : undefined;
        lot.expirationDate = lot.expirationDate ? moment(lot.expirationDate) : undefined;
        lot.retestDate = lot.retestDate ? moment(lot.retestDate) : undefined;
      });
    }
    return res;
  }
}
