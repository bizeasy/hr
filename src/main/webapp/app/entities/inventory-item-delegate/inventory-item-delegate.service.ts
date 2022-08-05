import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

type EntityResponseType = HttpResponse<IInventoryItemDelegate>;
type EntityArrayResponseType = HttpResponse<IInventoryItemDelegate[]>;

@Injectable({ providedIn: 'root' })
export class InventoryItemDelegateService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-item-delegates';

  constructor(protected http: HttpClient) {}

  create(inventoryItemDelegate: IInventoryItemDelegate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryItemDelegate);
    return this.http
      .post<IInventoryItemDelegate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryItemDelegate: IInventoryItemDelegate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryItemDelegate);
    return this.http
      .put<IInventoryItemDelegate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryItemDelegate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryItemDelegate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryItemDelegate: IInventoryItemDelegate): IInventoryItemDelegate {
    const copy: IInventoryItemDelegate = Object.assign({}, inventoryItemDelegate, {
      effectiveDate:
        inventoryItemDelegate.effectiveDate && inventoryItemDelegate.effectiveDate.isValid()
          ? inventoryItemDelegate.effectiveDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.effectiveDate = res.body.effectiveDate ? moment(res.body.effectiveDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventoryItemDelegate: IInventoryItemDelegate) => {
        inventoryItemDelegate.effectiveDate = inventoryItemDelegate.effectiveDate ? moment(inventoryItemDelegate.effectiveDate) : undefined;
      });
    }
    return res;
  }
}
