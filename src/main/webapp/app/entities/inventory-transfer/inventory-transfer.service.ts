import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryTransfer } from 'app/shared/model/inventory-transfer.model';

type EntityResponseType = HttpResponse<IInventoryTransfer>;
type EntityArrayResponseType = HttpResponse<IInventoryTransfer[]>;

@Injectable({ providedIn: 'root' })
export class InventoryTransferService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-transfers';

  constructor(protected http: HttpClient) {}

  create(inventoryTransfer: IInventoryTransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryTransfer);
    return this.http
      .post<IInventoryTransfer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryTransfer: IInventoryTransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryTransfer);
    return this.http
      .put<IInventoryTransfer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryTransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryTransfer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryTransfer: IInventoryTransfer): IInventoryTransfer {
    const copy: IInventoryTransfer = Object.assign({}, inventoryTransfer, {
      sentDate: inventoryTransfer.sentDate && inventoryTransfer.sentDate.isValid() ? inventoryTransfer.sentDate.toJSON() : undefined,
      receivedDate:
        inventoryTransfer.receivedDate && inventoryTransfer.receivedDate.isValid() ? inventoryTransfer.receivedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sentDate = res.body.sentDate ? moment(res.body.sentDate) : undefined;
      res.body.receivedDate = res.body.receivedDate ? moment(res.body.receivedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventoryTransfer: IInventoryTransfer) => {
        inventoryTransfer.sentDate = inventoryTransfer.sentDate ? moment(inventoryTransfer.sentDate) : undefined;
        inventoryTransfer.receivedDate = inventoryTransfer.receivedDate ? moment(inventoryTransfer.receivedDate) : undefined;
      });
    }
    return res;
  }
}
